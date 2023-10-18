import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useArguments } from '../../contexts/ArgumentContext';
import { useAuth } from '../../contexts/AuthContext';

const ArgumentDetails = () => {
    const { authInitialized } = useAuth();
    const { argumentId } = useParams();
    const { argument, fetchArgument } = useArguments();

    useEffect(() => {
        fetchArgument(argumentId);
    }, [authInitialized, argumentId]);

    if (!argument) {
        return "loading"
    }

    const renderPoint = (argumentPoint) => (
        <div>
            {argumentPoint}
        </div>
    )

    return (
        <>
            <h2> {argument.title} </h2>
            <h6>{argument.creationTimestamp}</h6>
            {argument.argumentDetails.map(renderPoint)}
        </>
    )
}


export default ArgumentDetails;