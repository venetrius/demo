
export const API_URL = process.env.REACT_APP_BE_URL || "http://localhost:8080"
let customUrl = ''

export const getApiUrl = () => {
    // if(customUrl) return customUrl
    return API_URL
}

console.log(getApiUrl())