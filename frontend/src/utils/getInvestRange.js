export const getMinInvest = (range) => {
    return Number(range.split("-")[0]);
}

export const getMaxInvest = (range) => {
    return Number(range.split("-")[1]);
}