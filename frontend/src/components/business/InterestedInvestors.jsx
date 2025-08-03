import "./InterestedInvestors.css"

const InterestedInvestors = ({ interestedInvestors }) => {
    return (
        <div className="interested-investors-container">
            {interestedInvestors.length > 0 ? (
                interestedInvestors.map((investor) => (
                    <div className="investor-card" key={investor.id}>
                        <img
                            className="investor-photo"
                            src={investor.photoUrl}
                            alt={investor.contactName}
                        />    
                        <div className="investor-details">
                            <h3 className="investor-name">{investor.contactName}</h3>
                            <p className="investor-info">{investor.contactEmail}</p>
                            <p className="investor-info">{investor.contactPhone}</p>
                            <p className="investor-info">{investor.state}</p>
                        </div>
                    </div>
                ))
            ) : (
                <div className="no-investor-msg">
                    <p>No interested investors yet, hand tight!</p>    
                    <p>Keep your profile updated and investors will find you soon.</p>
                </div>
            )}
        </div>
    );
}
 
export default InterestedInvestors;