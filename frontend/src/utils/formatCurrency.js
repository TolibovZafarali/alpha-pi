const formatCurrency = (num) => {
    if (isNaN(num)) return "";
    return num?.toLocaleString("en-US", { style: "currency", currency: "USD"});
}
 
export default formatCurrency;