import { useState } from "react";
import emailjs from "@emailjs/browser";
import "./Contact.css";

const Contact = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [message, setMessage] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [sending, setSending] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const templateParams = {
      from_name: name,
      from_email: email,
      phone: phone || "—",
      message,
      time: new Date().toLocaleString(),
      reply_to: email,
    };

    setSending(true);
    setSuccessMsg("");

    try {
      await emailjs.send(
        "service_n1xxg5b",
        "template_qlm96is",
        templateParams,
        "-efmrM26rTZPXQY6D"
      );

      setSuccessMsg("Your message has been sent! We’ll review it shortly.");
      setName("");
      setEmail("");
      setPhone("");
      setMessage("");
    } catch (err) {
      console.error("EmailJS Error:", err);
      setSuccessMsg("Oops! Something went wrong. Please try again later.");
    } finally {
      setSending(false);
    }
  };

  const remaining = 200 - message.length;

  return (
    <div className="contact-us-main">
      <div className="contact-us">
        <h1>Contact Us</h1>
        <form onSubmit={handleSubmit} noValidate>
          <label>
            Full Name:
            <input
              type="text"
              value={name}
              required
              onChange={(e) => setName(e.target.value)}
            />
          </label>

          <label>
            Email:
            <input
              type="email"
              value={email}
              required
              onChange={(e) => setEmail(e.target.value)}
            />
          </label>

          <label>
            Phone:
            <input
              type="text"
              value={phone}
              placeholder="Optional"
              onChange={(e) => setPhone(e.target.value)}
            />
          </label>

          <label>
            Message:
            <textarea
              value={message}
              required
              onChange={(e) => setMessage(e.target.value)}
              maxLength={200}
            />
          </label>

          <p>{remaining}/200</p>

          <button
            type="submit"
            disabled={!name || !email || !message || sending}
            aria-busy={sending}
          >
            {sending ? "Sending..." : "Send"}
          </button>

          {successMsg && <p className="success">{successMsg}</p>}
        </form>
      </div>
    </div>
  );
};

export default Contact;
