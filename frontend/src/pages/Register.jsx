import { useNavigate } from 'react-router-dom'

export default function Register() {
    const navigate = useNavigate()

    const registerForm = async (formData) => {
        
        const newFormObj = Object.fromEntries(formData);
        const formObjUpdated = {
            ...newFormObj,
            gpa: Number(newFormObj.gpa)
        }
       try {
        const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/auth/register`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formObjUpdated)
        })
           
        if(response.ok){
            navigate('/login')
        }
       }catch(error){
        console.log(error)
       } 
    }


    return (
        <div className="auth-page">
            <img src="src/assets/StudySyncLogo1.png" alt="StudySync Logo" className="auth-logo" />
            <div className="auth-card">
                <h2>Create Account</h2>
                <p className="auth-subtitle">Join StudySync and find your study group</p>
                <form className="auth-form-fields" action={registerForm}>
                    <div className="auth-grid">
                        <div className="auth-field">
                            <label className="auth-label" htmlFor="first_name">First Name</label>
                            <input className="auth-input" id="first_name" name="firstName" placeholder="John" />
                        </div>
                        <div className="auth-field">
                            <label className="auth-label" htmlFor="last_name">Last Name</label>
                            <input className="auth-input" id="last_name" name="lastName" placeholder="Doe" />
                        </div>
                        <div className="auth-field">
                            <label className="auth-label" htmlFor="major">Major</label>
                            <input className="auth-input" id="major" name="major" placeholder="Computer Science" />
                        </div>
                        <div className="auth-field">
                            <label className="auth-label" htmlFor="gpa">GPA</label>
                            <input className="auth-input" type="number" step="0.1" id="gpa" name="gpa" placeholder="3.5" />
                        </div>
                        <div className="auth-field">
                            <label className="auth-label" htmlFor="email">Email</label>
                            <input className="auth-input" type="email" id="email" name="email" placeholder="you@university.edu" />
                        </div>
                        <div className="auth-field">
                            <label className="auth-label" htmlFor="password">Password</label>
                            <input className="auth-input" type="password" id="password" name="password" placeholder="••••••••" />
                        </div>
                    </div>
                    <button type="submit" className="auth-submit-btn">Create Account</button>
                </form>
                <p className="auth-switch">Already have an account? <a href="/login">Login here</a></p>
            </div>
        </div>
    )
}