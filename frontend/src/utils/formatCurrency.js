const formatCurrency = (value) => {
    if (value === null || value === undefined || value === "") return "";

    const amount = typeof value === "number" ? value : Number(value);
    if (Number.isNaN(amount)) return "";

    return amount.toLocaleString("en-US", {
        style: "currency",
        currency: "USD",
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    });
};
 
export default formatCurrency;