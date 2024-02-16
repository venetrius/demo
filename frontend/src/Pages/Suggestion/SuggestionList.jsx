
import { useArguments } from '../../contexts/ArgumentContext';
import React, { useEffect, useState } from 'react';
import { Collapse, Tag } from 'antd';
import VoteButton from '../../components/Button/VoteButton'

const { Panel } = Collapse;

const getSuggestionStatusColor = {
    ACTIVE: 'blue',
    ACCEPTED: 'green',
    REJECTED: 'red'
}

const SuggestionList = ({ argument }) => {
    const { listSuggestions, suggestions, voteOnSuggestion, deleteSuggestionVote } = useArguments();
    const [activePanelKey, setActivePanelKey] = useState('1');

    useEffect(() => {
        listSuggestions(argument.id)
    }, [argument.id])

    if (!suggestions) return "Loading..."
    console.log({suggestions})
    const suggestionsByArgumentPoints = {}

    suggestions?.forEach(element => {
        suggestionsByArgumentPoints[element.section] = suggestionsByArgumentPoints[element.section] || []
        suggestionsByArgumentPoints[element.section].push(element)
    })

    return (
        <Collapse activeKey={activePanelKey} onChange={setActivePanelKey}>
            {Object.keys(suggestionsByArgumentPoints).map((section, index) => {
                const sectionText = argument.argumentDetails[section]
                const sectionTitle = `${section} - ${sectionText.slice(0,30)}...`
                return (
                    <Panel 
                        header={sectionTitle} 
                        key={index}>
                        {suggestionsByArgumentPoints[section].map((suggestion, index) => (
                            <div 
                                key={index}
                                style={{
                                    "border": "1px solid #d9d9d9",
                                    "marginBottom": "15px",
                                    "padding": "10px",
                                    "borderRadius": "4px",
                                    "boxShadow": "0 2px 8px rgba(0,0,0,0.1)"
                                }}
                            >
                                <h5>
                                    {suggestion.type}
                                    <Tag style={{ marginLeft: '5px' }} color={getSuggestionStatusColor[suggestion.status]}>
                                        {suggestion.status}
                                    </Tag>
                                </h5>
                                <p>{suggestion.text}</p>
                                <VoteButton 
                                    onVote={(voteType) => voteOnSuggestion(argument.id, suggestion.id, voteType)}
                                    onUnvote={() => deleteSuggestionVote(argument.id, suggestion.id)}
                                    votes={suggestion.numberOfLikes}
                                    userVote={suggestion.likedByCurrentUser ? 1 : 0}
                                />
                            </div>
                        ))}
                    </Panel>
                ) })}
        </Collapse>
    )
}

export default SuggestionList