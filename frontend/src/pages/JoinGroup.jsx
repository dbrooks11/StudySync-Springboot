import { useState, useEffect, Fragment } from "react";
import GroupLayout from "../components/GroupLayout";
import SuggestedGroups from "../components/Profile/SuggestedGroups";
import { useSearch } from '../SearchContext'

export default function JoinGroup() {
    const { searchTerm } = useSearch()
    const [recommendedGroups, setRecommendedGroups] = useState([])
    const [allGroups, setAllGroups] = useState([])

    const filteredRecommended = recommendedGroups.filter(group =>
        group.group.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        group.course.courseCode?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        group.course.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        group.location?.toLowerCase().includes(searchTerm.toLowerCase())
    )

    const filteredAll = allGroups.filter(group =>
        group.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        group.course.courseCode?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        group.course.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        group.location?.toLowerCase().includes(searchTerm.toLowerCase())
    )

    const fetchAllGroups = async() => {
        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/all`, {
                credentials: "include",
                method: "GET"
            })
            const data = await response.json()
            setAllGroups(data)
        }catch(error){
            console.error(error)
        }
    }

    const fetchRecommendedGroups = async() => {
        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/all/recommend`, {
                credentials: "include",
                method: "GET"
            })
            const data = await response.json()
            setRecommendedGroups(data)
            console.log(data)
        }catch(error){
            console.error(error)
        }
    }

    useEffect(() => {
    fetchRecommendedGroups()
    fetchAllGroups()
}, []);

    async function joinGroup(groupId) {
        try{
            await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/join/${groupId}`, {
                credentials: "include",
                method: "POST"
            })
            fetchRecommendedGroups()
            fetchAllGroups()
        }catch(error){
            console.error(error)
        }
    }

    async function leaveGroup(groupId) {
        try{
            await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/groups/leave/${groupId}`, {
                credentials: "include",
                method: "DELETE"
            })
            fetchRecommendedGroups()
            fetchAllGroups()
        }catch(error){
            console.error(error)
        }
    }

    return(
        <main className="courses-layout">
            <div className="groups-card">
                <SuggestedGroups 
                    groups={filteredRecommended}
                    onJoin={joinGroup}
                    onLeave={leaveGroup}
                />
            </div>

            <div className="courses-list-card">
                <h3>All Groups</h3>
                <div className="course-cards-container">
                    {filteredAll.map(group => (
                        <div key={group.id} className="group-card">
                            <GroupLayout {...group} />
                            {!group.isJoined
                                ? <button className="enroll-btn" onClick={() => joinGroup(group.id)}>Join</button>
                                : <button className="remove-btn" onClick={() => leaveGroup(group.id)}>Leave</button>
                            }
                        </div>
                    ))}
                </div>
            </div>
        </main>
    )
}

