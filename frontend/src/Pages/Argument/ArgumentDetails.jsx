import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useArguments } from '../../contexts/ArgumentContext';

const ArgumentDetails = () => {
    const { argumentId } = useParams();
    const { argument, fetchArgument } = useArguments();

    useEffect(() => {
        fetchArgument(argumentId);
    }, [argumentId]);

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