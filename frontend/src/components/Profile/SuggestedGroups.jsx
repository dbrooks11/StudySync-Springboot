export default function SuggestedGroups({ groups = [], onJoin, onLeave }) {
    return (
        <div className="suggestions">
            <h3>Recommended Groups</h3>
            <div className="group-container">
                {groups.map((group) => (
                    <div key={group.id} className="group-card">
                        <div className="group-name">{group.name}</div>
                        <div className="group-row">
                            <span className="group-label">Course</span>
                            <span>{group.courseCode}{group.courseName? ` — ${group.courseName}` : ''}</span>
                        </div>
                        <div className="group-row">
                            <span className="group-label">Location</span>
                            <span>{group.location ?? 'TBD'}</span>
                        </div>
                        <div className="group-row">
                            <span className="group-label">Meets</span>
                            <span>{group.meetingTime ?? 'TBD'}</span>
                        </div>
                        <div className="group-row">
                            <span className="group-label">Spots Left</span>
                            <span>{group.spotsLeft} / {group.maxSize}</span>
                        </div>
                        {!group.isJoined
                            ? <button className="group-join-btn" onClick={() => onJoin(group.id)}>Join</button>
                            : <button className="remove-btn" onClick={() => onLeave(group.id)}>Leave</button>
                        }
                    </div>
                ))}
            </div>
        </div>
    );
}