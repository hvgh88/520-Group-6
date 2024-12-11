import React, {useEffect, useState } from "react";
import axios from 'axios';
import { User, Mail, Building, Calendar, Edit2 } from "lucide-react";
import "./Profile.css";
import config from '../../config/config';
import { useUser } from '../../UserContext'; 

const Profile = () => {
  const { userEmail, userId } = useUser();
  // console.log("Email id inside profile " + userEmail) 
  // console.log("user id inside profile " + userId)
  const [isEditing, setIsEditing] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  
  const [profileData, setProfileData] = useState({
    firstName: "",
    middleName: "",
    lastName: "",
    email: userEmail,
    department: "",
    graduationYear: "",
    bio: "",
  });

  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        // Call the backend service with email
        const response = await axios.get(`${config.USER_SERVICE}/profile/kaushik@umass.edu`);
        const { code, data, message } = response.data;

        if (response.status === 200 && code === 10006) {
          // Update state with user profile data
          setProfileData({
            firstName: data.firstName || "",
            middleName: data.middleName || "",
            lastName: data.lastName || "",
            email: data.email || "",
            department: data.department || "",
            graduationYear: data.graduationYear && data.graduationYear !== 0 ? data.graduationYear : null,
            bio: data.bio || "",
          });
        } else {
          setErrorMessage(message || "Failed to fetch user profile.");
        }
      } catch (error) {
        setErrorMessage("Error fetching user profile. Please try again.");
        console.error("Error fetching user profile:", error);
      }
    };

    fetchUserProfile();
  }, [userEmail]);


  useEffect(() => {
    console.log(profileData.bio);
    console.log(profileData.firstName);
    console.log(profileData.middleName);
    console.log(profileData.lastName);
    console.log(profileData.department);
    console.log(profileData.graduationYear);
  }, [profileData]); 

  const [editedData, setEditedData] = useState({...profileData});


  const handleEditClick = () => {
    setEditedData({
      firstName: profileData.firstName,
      middleName: profileData.middleName,
      lastName: profileData.lastName,
      email: profileData.email,
      department: profileData.department,
      graduationYear: profileData.graduationYear,
      bio: profileData.bio,
    });
    setIsEditing(!isEditing);
  };

  const handleSave = async (e) => {
    e.preventDefault();
  
    try {
      // Sending the updated profile data to the backend API
      const response = await axios.post(`${config.USER_SERVICE}/profile`, {
        email: profileData.email,
        firstName: editedData.firstName,
        middleName: editedData.middleName,
        lastName: editedData.lastName,
        department: editedData.department,
        graduationYear: editedData.graduationYear,
        bio: editedData.bio,
      });
  
      // Handle the response
      const { code, message, data } = response.data;
      if (response.status === 200) {
        alert("Profile updated successfully!");
        setProfileData(editedData); // Update the profile data with the latest values // Close the edit form
      } else {
        alert(message || "Failed to update profile.");
      }

      setIsEditing(false);
    } catch (error) {
      console.error("Error saving profile:", error);
      alert("An error occurred while saving the profile. Please try again.");
    }
  };

  return (
    <div className="profile-container">
      {/* Profile Card */}
      <div className="profile-card">
        <div className="profile-header">
          <div className="profile-avatar">
            <User className="avatar-icon" />
          </div>
          <div className="profile-info">
            <div className="name-section">
              <h2>{profileData.firstName} {profileData.middleName} {profileData.lastName}</h2>
              <button
                className="edit-button"
                onClick={handleEditClick}
              >
                <Edit2 className="edit-icon" />
              </button>
            </div>
            <p className="profile-subtitle">
              {profileData.department} • Class of {profileData.graduationYear}
            </p>
          </div>
        </div>

        {isEditing ? (
          <form onSubmit={handleSave} className="edit-form">
            <div className="form-row">
              <div className="form-group">
                <label>First Name</label>
                <input
                  type="text"
                  value={editedData.firstName}
                  onChange={(e) => setEditedData({...editedData, firstName: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Middle Name</label>
                <input
                  type="text"
                  value={editedData.middleName}
                  onChange={(e) => setEditedData({...editedData, middleName: e.target.value})}
                  placeholder="Optional"
                />
              </div>
              <div className="form-group">
                <label>Last Name</label>
                <input
                  type="text"
                  value={editedData.lastName}
                  onChange={(e) => setEditedData({...editedData, lastName: e.target.value})}
                  required
                />
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label>Department</label>
                <input
                  type="text"
                  value={editedData.department}
                  onChange={(e) => setEditedData({...editedData, department: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Graduation Year</label>
                <input
                  type="number"
                  value={editedData.graduationYear}
                  onChange={(e) => setEditedData({...editedData, graduationYear: e.target.value})}
                  min="2024"
                  max="2030"
                  required
                />
              </div>
            </div>

            <div className="form-group">
              <label>Bio</label>
              <textarea
                value={editedData.bio}
                onChange={(e) => setEditedData({...editedData, bio: e.target.value})}
                placeholder="Tell us something about yourself"
              />
            </div>

            <div className="form-actions">
              <button type="button" className="cancel-button" onClick={() => setIsEditing(false)}>
                Cancel
              </button>
              <button type="submit" className="save-button">
                Save Changes
              </button>
            </div>
          </form>
        ) : (
          <div className="profile-details">
            <div className="detail-item">
              <Mail className="detail-icon" />
              <span>{profileData.email}</span>
            </div>
            <div className="detail-item">
              <Building className="detail-icon" />
              <span>{profileData.department}</span>
            </div>
            <div className="detail-item">
              <Calendar className="detail-icon" />
              <span>Class of {profileData.graduationYear}</span>
            </div>
            <div className="bio-section">
              <h3>About</h3>
              <p>{profileData.bio}</p>
            </div>
          </div>
        )}
      </div>

      {/* break */}
    </div>
  );
};

export default Profile;