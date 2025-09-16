const parseInterests = (value) => {
    if (typeof value !== "string") return [];

    return value
        .split(",")
        .map((interest) => interest.trim())
        .filter(Boolean);
};

export default parseInterests;