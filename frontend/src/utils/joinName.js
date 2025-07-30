const getFullName = (first, last) => {
    first = first.trim();
    last = last.trim();
    
    return (`${first} ${last}`.trim());
}
 
export default getFullName;