import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Collapse } from 'antd';
import { useArguments } from '../../contexts/ArgumentContext';
import { useAuth } from '../../contexts/AuthContext';
import SuggestionForm from '../Suggestion/SuggestionForm';

const { Panel } = Collapse;

const ArgumentDetails = () => {
    const { authInitialized } = useAuth();
    const { argumentId } = useParams();
    const { argument, fetchArgument } = useArguments();
    const [activePanelKey, setActivePanelKey] = useState('1');

    useEffect(() => {
        fetchArgument(argumentId);
    }, [authInitialized, argumentId]);

    if (!argument) {
        return "loading"
    }

    const handleTogglePanel = (key) => {
        setActivePanelKey(key);
    }
    const renderPoint = (argumentPoint, index) => (
        <div key={index} style={{ marginBottom: '10px' }}>
            • {argumentPoint}
        </div>
    );

    return (
        <>
            {/* <Link to={`/discussions/${argument.discussionID}`}>
                <Button style={{ color: 'var(--primary-color)' }}>Back</Button>
            </Link> */}
            <h2 style={{ marginTop: '20px', marginBottom: '10px' }}>{argument.title}</h2>
            <h6><i className="fa fa-clock-o" aria-hidden="true"></i> {argument.creationTimestamp}</h6>
            {argument.argumentDetails.map(renderPoint)}

            <Collapse activeKey={activePanelKey} onChange={handleTogglePanel}>
                <Panel header="Show Suggestions" key="1">
                    Work in progress
                </Panel>
                <Panel header="Create New Suggestion" key="2">
                    <SuggestionForm argument={argument}/>
                </Panel>
            </Collapse>
        </>
    );
};

export default ArgumentDetails;
