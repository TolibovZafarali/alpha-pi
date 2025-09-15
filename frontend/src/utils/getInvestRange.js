const parseRangeValue = (value) => {
    if (typeof value !== "string") return null;

    const trimmed = value.trim();
    if (trimmed === "") return null;

    const parsed = Number(trimmed);
    return Number.isNaN(parsed) ? null : parsed;
};

export const getMinInvest = (range) => {
    if (typeof range !== "string") return null;

    const [min = ""] = range.split("-");
    return parseRangeValue(min);
}

export const getMaxInvest = (range) => {
    if (typeof range !== "string") return null;

    const [, max = ""] = range.split("-");
    return parseRangeValue(max);
}