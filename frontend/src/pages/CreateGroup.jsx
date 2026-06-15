import { useState, useEffect } from "react"
import GroupLayout from "../components/GroupLayout"

export default function CreateGroup() {
    const [courseList, setCourseList] = useState([])
    const [createdGroups, setCreatedGroups] = useState([])

    async function createGroup(formData) {
        const formObj = Object.fromEntries(formData)
        const updatedFormObj = {
            ...formObj,
            maxSize: Number(formObj.maxSize),
            meetingTime: new Date(formObj.meetingTime).toISOString()
        }
        try{
            await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/create/${formObj.courseId}`, {
                credentials: "include",
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedFormObj)
            })
        
            await getCreatedGroups()
        }catch(error){
            console.error(error)
        }
    }

    async function deleteGroup(groupId, courseId) {
        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/delete/${groupId}/${courseId}`, {
                credentials: "include",
                method: "DELETE"
            })
            const data = await response.json()
            setCreatedGroups(data)
        }catch(error){
            console.error(error)
        }
    }

    async function getCourseList() {
        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/courses/course-list`, {
                credentials: "include",
                method: "GET"
            })
            const data = await response.json()
            setCourseList(data)
        }catch(error){
            console.error(error)
        }
    }

    async function getCreatedGroups() {
        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/me`, {
                credentials: "include"
            })
            const data = await response.json()
            setCreatedGroups(data.myGroups)
        }catch(error){
            console.error(error)
        }
    }

    useEffect(() => {
        getCourseList()
        getCreatedGroups()
    }, []);

    return(
        <main className="courses-layout">
            <div className="courses-form-card">
                <h2>Create a Study Group</h2>
                <p className="courses-subtitle">Set up a new study group for your course — your classmates will be able to find and join it.</p>
                <form className="course-form" action={createGroup}>
                    <div className="create-group-grid">
                        <div className="create-group-field">
                            <label className="create-group-label" htmlFor="group-name">Group Name</label>
                            <input placeholder="e.g. Monday Crammers" name="name" id="group-name" />
                        </div>
                        <div className="create-group-field">
                            <label className="create-group-label" htmlFor="location">Location</label>
                            <input placeholder="e.g. Dirac Library" name="location" id="location" />
                        </div>
                        <div className="create-group-field">
                            <label className="create-group-label" htmlFor="group-size">Group Size</label>
                            <input placeholder="e.g. 5" id="group-size" name="maxSize" type="number" />
                        </div>
                        <div className="create-group-field">
                            <label className="create-group-label" htmlFor="meeting">Meeting Time</label>
                            <input type="datetime-local" id="meeting" name="meetingTime" />
                        </div>
                        <div className="create-group-field">
                            <label className="create-group-label" htmlFor="courses">Course</label>
                            <select name="courseId" id="courses" className="create-group-select">
                                {courseList.map(course => (
                                    <option key={course.courseId} value={course.courseId}>
                                        {course.courseCode} - {course.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>
                    <button type="submit" className="submit-btn">Create Group</button>
                </form>
            </div>

            <div className="courses-list-card">
                <h3>Your Created Groups</h3>
                <div className="course-cards-container">
                    {createdGroups.map((group) => (
                        <div key={group.id} className="group-card">
                            <GroupLayout {...group} />
                            <button className="remove-btn" type="button" onClick={() => deleteGroup(group.id, group.course.courseId)}>
                                Delete
                            </button>
                        </div>
                    ))}
                </div>
            </div>
        </main>
    )
}