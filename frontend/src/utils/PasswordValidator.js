const PasswordValidator = (str) => {
    return (/\d/.test(str)) && (/[a-zA-Z]/.test(str)) && (str.length >= 8);
}
 
export default PasswordValidator;