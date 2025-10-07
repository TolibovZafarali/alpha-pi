import formatPhoneNumber from "../../utils/getPhoneNumber";
import "./InterestedInvestors.css";

const InterestedInvestors = ({ interestedInvestors, onMessage }) => {
  return (
    <div className="interested-investors-container">
      {interestedInvestors.length > 0 ? (
        interestedInvestors.map((investor, index) => (
          <div key={investor.id || investor.contactEmail || index} className="investor-card">
            <img className="investor-photo" src={investor.photoUrl} alt={investor.contactName} />

            <div className="investor-card-main">
              <h3 className="investor-name">{investor.contactName}</h3>

              <div className="investor-details">
                <p className="investor-info">{investor.contactEmail}</p>
                <p className="investor-info">{formatPhoneNumber(investor.contactPhone)}</p>
                <p className="investor-info">{investor.state}</p>
              </div>
            </div>

            <button type="button" className="message-button" onClick={() => onMessage?.(investor)}>
              Message
            </button>
          </div>
        ))
      ) : (
        <div className="no-investor-msg">
          <p>No interested investors yet, hang tight!</p>
          <p>Keep your profile updated and investors will find you soon.</p>
        </div>
      )}
    </div>
  );
};

export default InterestedInvestors;