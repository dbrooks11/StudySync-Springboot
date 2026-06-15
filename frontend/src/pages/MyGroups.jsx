import { useEffect, useState } from "react";
import GroupLayout from "../components/GroupLayout";


export default function MyGroups(){

    const [createdGroups, setCreatedGroups] = useState([])
    const [joinedGroups, setJoinedGroups ] = useState([])

    useEffect(() => {
        const fetchMyGroups = async() => {
            try {
                const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/me`, {
                    credentials: "include",
                    method: "GET"
                })

                const data = await response.json()

                setCreatedGroups(data.myGroups)
                setJoinedGroups(data.joinedGroups)

            }catch(error){
                console.error(error)
            }
        }
        
        fetchMyGroups()
        
    }, []);

    async function deleteGroup(groupId, courseId) {
        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/delete/${groupId}/${courseId}`, {
                credentials: "include",
                method: "DELETE"
            })

            const data = await response.json()

            setCreatedGroups(data.myGroups)

        }catch(error){
            console.error(error)
        }
    }

    async function leaveGroup(groupId) {
        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/leave/${groupId}`, {
                credentials: "include",
                method: "DELETE"
            })

            const data = await response.json()
            setJoinedGroups(data.joined_groups)

        }catch(error){
            console.error(error)
        }
    }

    return(
        <section className="group-container">
            <div>
                <h3>Your Created Groups</h3>
                {createdGroups.map((group) => {
                    return(
                        <div className="group" key={group.id}>
                            <GroupLayout
                                {...group}
                            />
                            <button type="button" onClick={() => deleteGroup(group.id, group.course.courseId)}>Delete</button>
                        </div>
                    )
                })}
            </div>
            <div>
                <h3>Your Joined Groups</h3>
                {joinedGroups.map((group) => {
                    return(
                        <div className="group" key={group.id}>
                            <GroupLayout 
                                {...group}
                            />
                            <button type="button" onClick={() => leaveGroup(group.id)}>Leave</button>
                        </div>
                    )
                })}
            </div>
        </section>
    )
}