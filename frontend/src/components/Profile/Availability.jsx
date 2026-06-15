import { formatTime } from '../../util/datetimeConversion'


export default function Availability({availabilities = [], setProfile}) {

    const addAvailability = async (formData) => {
        
        const newFormObj = Object.fromEntries(formData);
        const updatedObj = {
            ...newFormObj,
            startTime: newFormObj.startTime + ":00",
            endTime: newFormObj.endTime + ":00"
        }

        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/profile/availability/add`, {
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                method: "POST",
                body: JSON.stringify(updatedObj)
            })

            const data = await response.json()

            if(!response.ok){
                throw new Error(data.error)
            }
            setProfile((profile) => {
                return {
                    ...profile,
                    'availabilities': data
                }
            })
        }catch(error){
            console.log(error)
        }
    }

    const deleteAvailability = async(availId, day) => {
        try{
            const response = await fetch(`${import.meta.env.VITE_REACT_APP_API_URL}/profile/availability/delete/${availId}/${day}`, {
                credentials: "include",
                method: "DELETE",
            })

            const data = await response.json()

            if(!response.ok){
                throw new Error(data.error)
            }
            setProfile((profile) => {
                return {
                    ...profile,
                    'availabilities': data
                }
            })
            console.log(data)
        }catch(error){
            console.log(error)
        }
    }

    return(
        <section className='avail-container'>
                <div className='avail-header'>
                    <div>
                        <h3>Availability</h3>
                        <p className="avail-subtitle">Add your weekly free windows below - we'll use them to find groups that fit your schedule.</p>
                    </div>
                </div>
            <form className='avail-form' onSubmit={(e) => {
                e.preventDefault();
                const formData = new FormData(e.target);
                addAvailability(formData);
            }}>
                <div>
                    <label htmlFor='day'>Day</label>
                    <input list='days' name='day' id='day'></input>
                    <datalist id="days">
                        <option value={'Monday'}></option>
                        <option value={'Tuesday'}></option>
                        <option value={'Wednesday'}></option>
                        <option value={'Thursday'}></option>
                        <option value={'Friday'}></option>
                        <option value={'Saturday'}></option>
                        <option value={'Sunday'}></option>
                    </datalist>
                </div>
                <div>
                    <label htmlFor='start-time'>Start Time:</label>
                    <input type='time' id='start-time' name='startTime' min={'00:00'} max={'24:00'}></input>
                </div>
                <div>
                    <label htmlFor='end-time'>End Time:</label>
                    <input type='time' id='start-time' name='endTime' min={'00:00'} max={'24:00'}></input>
                </div>
                <button type='submit'>+</button>
            </form>
            <div className='availabilities-container'>
                {availabilities.map(availability => {
                    return(
                        <div key={availability.id} className='availabilities'>
                            <div>
                                <span>{availability.day}</span>
                                <div>
                                    <span>{formatTime(availability.startTime)}</span>
                                    <span>-</span>
                                    <span>{formatTime(availability.endTime)}</span>
                                </div>
                            </div>
                            <button 
                                type='button' 
                                onClick={() => deleteAvailability(availability.id, availability.day)}
                            >
                                Delete
                            </button>
                        </div>
                    )
                })}
            </div>
        </section>
    )
}