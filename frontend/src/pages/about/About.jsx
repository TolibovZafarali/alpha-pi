import "./About.css";
import { FaGithub, FaLinkedin, FaGlobe } from "react-icons/fa";

const About = () => {
  return (
    <div className="about-page">
      <section className="about-section">
        <h1>About Alpha-Pi</h1>
        <p>
          Alpha-Pi is a full-stack platform that connects small business owners with potential investors in a simple and approachable way. 
          Business owners can create professional profiles with real business metrics, while investors can browse, filter, 
          and save startups that align with their industry interests and investment range.
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
