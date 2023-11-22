
import { useArguments } from '../../contexts/ArgumentContext';
import React, { useEffect, useState } from 'react';
import { Collapse } from 'antd';

const { Panel } = Collapse;


const SuggestionList = ({ argument }) => {
    const { listSuggestions, suggestions } = useArguments();
    const [activePanelKey, setActivePanelKey] = useState('1');


    useEffect(() => {
        listSuggestions(argument.id)
    }, [argument.id])

    if (!suggestions) return "Loading..."

    const suggestionsByArgumentPoints = {}

    suggestions?.forEach(element => {
        suggestionsByArgumentPoints[element.section] = suggestionsByArgumentPoints[element.section] || []
        suggestionsByArgumentPoints[element.section].push(element)
    })

    return (
        <Collapse activeKey={activePanelKey} onChange={setActivePanelKey}>
            {Object.keys(suggestionsByArgumentPoints).map((section, index) => {
                const sectionText = argument.argumentDetails[section]
                const sectionTitle = `${index + 1} - ${sectionText.slice(0,30)}...`
                return (
                    <Panel header={sectionTitle} key={index}>
                        {suggestionsByArgumentPoints[section].map((suggestion, index) => (
                            <li key={index}>
                                <h5>{suggestion.type}</h5>
                                <p>{suggestion.text}</p>
                            </li>
                        ))}
                    </Panel>
                ) })}
        </Collapse>
    )
}

export default SuggestionList