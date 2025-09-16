const getErrorMessage = (error, fallbackMessage = "Something went wrong.") => {
    const data = error?.response?.data;
  
    if (!data) return fallbackMessage;
    if (typeof data === "string") return data;
  
    if (typeof data?.message === "string" && data.message.trim()) {
        return data.message;
    }
  
    if (typeof data?.error === "string" && data.error.trim()) {
        return data.error;
    }
  
    return fallbackMessage;
  };
  
export default getErrorMessage;