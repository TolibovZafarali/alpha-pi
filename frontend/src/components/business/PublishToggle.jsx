import "./PublishToggle.css";

const PublishToggle = ({ checked, disabled, onChange }) => {
    return (
        <div
            className={`publish-toggle-switch${checked ? " checked" : ""}${disabled ? " disabled" : ""}`}
            onClick={() => !disabled && onChange(!checked)}
            tabIndex={disabled ? -1 : 0}
            role="switch"
            aria-checked={checked}
            aria-disabled={disabled}
        >
            <div className="publish-toggle-slider" />
        </div>
    );
};

export default PublishToggle;