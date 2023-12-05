export const hashPassword = async (payload) => {
    try {
        const encoder = new TextEncoder();
        const data = encoder.encode(payload);
        const hash = await crypto.subtle.digest('SHA-256', data);
        const hashArray = Array.from(new Uint8Array(hash));
        const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
        return hashHex
    } catch (err) {
        console.error(err);
    }
};

