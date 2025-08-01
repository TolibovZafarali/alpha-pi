function formatPhoneNumber(phoneNumber) {
  const digitsOnly = phoneNumber.replace(/\D/g, '');
  if (digitsOnly.length !== 10) {
    return "Invalid phone number";
  }

  const areaCode = digitsOnly.slice(0, 3);
  const firstPart = digitsOnly.slice(3, 6);
  const secondPart = digitsOnly.slice(6, 10);

  return `+1 (${areaCode}) ${firstPart}-${secondPart}`;
}

export default formatPhoneNumber;