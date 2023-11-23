const requestHandler = (token) => {
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
    }

    const get = url =>  fetch(url, { headers });
    const post = (url, body) => fetch(url, { method: 'POST', headers, body });
    const put = (url, body) => fetch(url, { method: 'PUT', headers, body });
    const del = url => fetch(url, { method: 'DELETE', headers });

    return {
        get,
        post,
        put,
        del,
    }
}


export default requestHandler;
