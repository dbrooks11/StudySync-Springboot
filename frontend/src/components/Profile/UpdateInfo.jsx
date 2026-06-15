


export default function UpdateInfoForm({label, name, value, setProfile, openForm}){

    async function updateProfile(formData) {
        const formObj = Object.fromEntries(formData);

        try{
        const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/profile/edit`, {
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            method: "PATCH",
            body: JSON.stringify(formObj)
        })
        
        const data = await response.json();
        
        setProfile(prevProfile => {
            return (
                {
                    ...prevProfile,
                    ...data
                    
                }
            )
        })
        openForm(false)
        } catch(error){
            console.log(error)
        }
    }

    return(
        <form action={updateProfile} className="update-form">
            <button type="button" className="close-btn" onClick={() => openForm(false)}>X</button>
            <div className="update-container">
                <label htmlFor={label}>{label}</label>
                <input name={name} id={label} defaultValue={value}></input>
            </div>
            <button type="submit">Update {label}</button>
        </form>
    )
}