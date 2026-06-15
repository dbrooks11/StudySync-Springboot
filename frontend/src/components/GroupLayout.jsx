export default function GroupLayout(props) {
    
    function formatMettingTime(dateTime) {
        const newDate = new Date(dateTime);
        return newDate.toLocaleString()
    }
    return(
        <div>
            <div className="group-name">{props.name}</div>
            <div className="group-row">
                <span className="group-label">Course</span>
                <span>{props.course?.courseCode ?? props.courseCode}{props.course?.name ?? props.courseName ? ` - ${props.course?.name ?? props.courseName}` : ''}</span>
            </div>
            <div className="group-row">
                <span className="group-label">Location</span>
                <span>{props.location ?? 'TBD'}</span>
            </div>
            <div className="group-row">
                <span className="group-label">Meets</span>
                <span>{formatMettingTime(props.meetingTime) ?? 'TBD'}</span>
            </div>
            <div className="group-row">
                <span className="group-label">Spots Left</span>
                <span>{props.spotsLeft ?? props.maxSize} / {props.maxSize}</span>
            </div>
        </div>
    )
}