const normalize = (value) => {
    if (typeof value !== "string") {
        return "";
    }
    return value.trim();
}

const getFullName = (first, last) => {
    const normalizedFirst = normalize(first);
    const normalizedLast = normalize(last);

    return (`${normalizedFirst} ${normalizedLast}`.trim());
}
 
export default getFullName;