import formatPhoneNumber from "../../utils/getPhoneNumber";
import "./InterestedInvestors.css"

const InterestedInvestors = ({ interestedInvestors }) => {
    return (
        <div className="interested-investors-container">
            {interestedInvestors.length > 0 ? (
                interestedInvestors.map((investor, index) => (
                    <div key={investor.id || index} className="investor-card">
                        <img
                            className="investor-photo"
                            src={investor.photoUrl}
                            alt={investor.contactName}
                        />    
                            
                        <h3 className="investor-name">{investor.contactName}</h3>
                        
                        <div className="investor-details">
                            <p className="investor-info">{investor.contactEmail}</p>
                            <p className="investor-info">{formatPhoneNumber(investor.contactPhone)}</p>
                            <p className="investor-info">{investor.state}</p>
                        </div>
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
}
 
export default InterestedInvestors;