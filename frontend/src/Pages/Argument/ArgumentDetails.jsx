import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { Button, Collapse, Divider, List } from 'antd';
import { useArguments } from '../../contexts/ArgumentContext';
import { useAuth } from '../../contexts/AuthContext';
import SuggestionForm from '../Suggestion/SuggestionForm';
import SuggestionList from '../Suggestion/SuggestionList';

const { Panel } = Collapse;

const ArgumentDetails = () => {
    const { authInitialized } = useAuth();
    const { argumentId } = useParams();
    const { argument, fetchArgument, listSuggestions, suggestions } = useArguments();
    const [activePanelKey, setActivePanelKey] = useState('1');

    useEffect(() => {
        fetchArgument(argumentId);
        listSuggestions(argumentId);
    }, [authInitialized, argumentId]);

    if (!argument) {
        return "loading"
    }

    const handleTogglePanel = (key) => {
        setActivePanelKey(key);
    }
        
    return (
        <>
            <Link to={`/discussions/${argument.discussionID}`}>
                <Button style={{ color: 'var(--primary-color)' }}>Back</Button>
            </Link>
            <h2 style={{ marginTop: '20px', marginBottom: '10px' }}>{argument.title}</h2>
            <h6><i className="fa fa-clock-o" aria-hidden="true"></i> {argument.creationTimestamp}</h6>

            <List
                size="small"
                header={<b>Argument sections</b>}
                bordered
                dataSource={argument.argumentDetails}
                renderItem={(item, index) => <List.Item>{`${index} - ${item}`}</List.Item>}
            />
            <Divider orientation="left" />

            <Collapse activeKey={activePanelKey} onChange={handleTogglePanel}>
                <Panel header="Show Suggestions" key="1">
                    <SuggestionList argument={argument} />
                </Panel>
                <Panel header="Create New Suggestion" key="2">
                    <SuggestionForm argument={argument} />
                </Panel>
            </Collapse>
        </>
    );
};

export default ArgumentDetails;
