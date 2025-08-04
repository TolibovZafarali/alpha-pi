class SignupRequest {
    constructor(email, password, contactName, contactEmail) {
        this.email = email;
        this.password = password;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
}

export default SignupRequest;