import { useEffect, useState } from "react";
import { useSearch } from '../SearchContext'


export default function Courses(){
    const { searchTerm } = useSearch()

    const [courseList, setCourseList] = useState([])
    const [enrolledCourses, setEnrolledCourses] = useState([])

    const filteredCourseList = courseList.filter(course =>
        course.courseCode.toLowerCase().includes(searchTerm.toLowerCase()) ||
        course.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        course.department?.toLowerCase().includes(searchTerm.toLowerCase())
    )

    const filteredEnrolled = enrolledCourses.filter(course =>
        course.courseCode.toLowerCase().includes(searchTerm.toLowerCase()) ||
        course.name?.toLowerCase().includes(searchTerm.toLowerCase())
    )

    

    useEffect(() => {
        const getCourses = async() => {
            try {
                const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/courses/all`, {
                credentials: "include",
                method: "GET"
            })

            const data = await response.json()

            setCourseList(data.courseList)
            setEnrolledCourses(data.enrolledCourses)

            }catch(error){
                console.error(error)
            }
        }

        getCourses()
    }, []);

    const handleCourseListSubmit = async (formData) => {
        
        const formObj = Object.fromEntries(formData)
        const updatedFormObj = {
            ...formObj,
            credits: Number(formObj.credits)
        }

        try {
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/courses/course-list/add`, {
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                method: "POST",
                body: JSON.stringify(updatedFormObj)
            })

            const data = await response.json()

            setCourseList(data)

        }catch(error){
            console.error(error)
        }
    };

    const enrollCourse = async (courseId, courseCode) => {
        try {
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/courses/enroll`, {
                credentials: "include",
                headers: { 'Content-Type': 'application/json' },
                method: "POST",
                body: JSON.stringify({
                    courseId: courseId,
                    courseCode: courseCode
                })
            })

            const data = await response.json()

            setEnrolledCourses(data)
        }catch(error){
            console.error(error)
        }
    }

    const deleteEnrolledCourse = async(courseId, courseCode) => {
        try {
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/courses/enroll/delete/${courseId}/${courseCode}`, {
                credentials: "include",
                method: "DELETE"
            })

            const data = await response.json()

            setEnrolledCourses(data)
        }catch(error){
            console.error(error)
        }
    }

    return (
    <main className="courses-layout">
        <div className="courses-form-card">
            <h2>Add a Course</h2>
            <p className="courses-subtitle">Don't see your course? Add it to the list then enroll!</p>
            <form className="course-form" action={handleCourseListSubmit}>
                <div className="course-grid">
                    <input placeholder="Course Code (ex. COP3330)" name="courseCode" />
                    <input placeholder="Course Name" name="name" />
                    <input placeholder="Department" name="department" />
                    <input placeholder="Credit Hours" name="credits" type="number" />
                </div>
                <button type="submit" className="submit-btn">Add Course</button>
            </form>
        </div>

        <div className="courses-lists">
            <div className="courses-list-card">
                <h3>Enrolled Courses</h3>
                <div className="course-cards-container">
                    {filteredEnrolled.map((course) => (
                        <div className="course-card" key={course.courseId}>
                            <div className="course-card-top">
                                <span className="course-code">{course.courseCode}</span>
                                <span className="course-name">{course.name ?? 'N/A'}</span>
                            </div>
                            <div className="course-card-bottom">
                                <div className="course-detail">
                                    <span className="course-label">Department</span>
                                    <span>{course.department ?? 'N/A'}</span>
                                </div>
                                <div className="course-detail">
                                    <span className="course-label">Credits</span>
                                    <span>{course.credits ?? 'N/A'}</span>
                                </div>
                            </div>
                            <button className="remove-btn" type="button" onClick={() => deleteEnrolledCourse(course.courseId, course.courseCode)}>
                                Remove
                            </button>
                        </div>
                    ))}
                </div>
            </div>
            <div className="courses-list-card">
                <h3>Course List</h3>
                <div className="course-cards-container">
                    {filteredCourseList.map((course) => (
                        <div className="course-card" key={course.courseId}>
                            <div className="course-card-top">
                                <span className="course-code">{course.courseCode}</span>
                                <span className="course-name">{course.name ?? 'N/A'}</span>
                            </div>
                            <div className="course-card-bottom">
                                <div className="course-detail">
                                    <span className="course-label">Department</span>
                                    <span>{course.department ?? 'N/A'}</span>
                                </div>
                                <div className="course-detail">
                                    <span className="course-label">Credits</span>
                                    <span>{course.credits ?? 'N/A'}</span>
                                </div>
                            </div>
                            <button className="enroll-btn" type="button" onClick={() => enrollCourse(course.courseId, course.courseCode)}>
                                Enroll
                            </button>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    </main>
    );
}