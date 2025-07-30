import "./About.css";
import { FaGithub, FaLinkedin, FaGlobe } from "react-icons/fa";

const About = () => {
  return (
    <div className="about-page">
      <section className="about-section">
        <h1>About Alpha-Pi</h1>
        <p>
          Alpha-Pi is a platform designed to bridge the gap between small business
          owners and investors. It allows business owners to create polished profiles,
          showcase their business goals, and connect with interested investors.
          Meanwhile, investors can browse startups by industry, save favorites,
          and engage in meaningful conversations. The platform also includes a
          built-in AI Mentor to support founders on their entrepreneurial journey.
        </p>
      </section>

        <hr />

      <section className="about-section">
        <h1>About Me</h1>
        <p>
          My name is Zafarali Tolibov, a full-stack developer with a background in
          finance and a passion for building intuitive software. Alpha-Pi was born
          out of a desire to simplify startup investing and empower small businesses
          with the tools they need to grow. Through this project, I combined my
          coding skills and business experience to create something impactful and
          easy to use.
        </p>

        <div className="social-icons">
          <a href="https://github.com/TolibovZafarali" target="_blank" rel="noopener noreferrer">
            <FaGithub />
          </a>
          <a href="https://linkedin.com/in/zafarali-tolibov" target="_blank" rel="noopener noreferrer">
            <FaLinkedin />
          </a>
          <a href="https://tolibovzafarali.github.io" target="_blank" rel="noopener noreferrer">
            <FaGlobe />
          </a>
        </div>
      </section>
    </div>
  );
};

export default About;
