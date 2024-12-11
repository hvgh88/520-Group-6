import React, { useEffect, useState } from "react";
import { Users, Calendar, MapPin, X, Search } from "lucide-react";
import "./Groups.css";
import config from "../../config/config";
import axios from "axios";
import { useUser } from '../../UserContext'; 

const Groups = () => {
  const { userEmail, userId } = useUser();
  console.log("user id inside profile " + userId)
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    console.log("Current groups state:", groups);
  }, [groups]); 

  // Fetch groups from the backend
  useEffect(() => {
    console.log("groups changed!");
    const fetchGroups = async () => {
      try {
        const response = await axios.get(`${config.USER_DASH}/group/all`);
        console.log(response.data);
        setGroups(response.data);
        // setGroups((prevGroups) => {
        //   console.log("entering setGroups");
        //   return [...response.data,...prevGroups]
        // });
        console.log("final setGroups", groups);
      } catch (error) {
        console.error("Error fetching groups:", error);
      }
    };

    fetchGroups();
  }, []);

  const [newGroup, setNewGroup] = useState({
    name: "",
    dateTime: "",
    location: "",
  });

  const handleCreateGroup = async (e) => {
    e.preventDefault();
    console.log("Creating new group:", newGroup);

    try {
      const response = await axios.post(`${config.USER_DASH}/group/create?userId=${userId}`, {
        name: newGroup.name,
        location: newGroup.location,
        dateTime: newGroup.dateTime,
      });

      console.log("response", response);
      if (response.status === 201) {
        // Assuming the response contains the new group, you can directly add it to the state
        const res = response.data; // The returned data should contain the newly created group
        const match = res.match(/ID: (\d+)/);
        let grpId = undefined;

        if (match) {
          grpId = match[1];  
          console.log('groupId', grpId);  
        }
  
        // Update the groups state with the new group
        setGroups((prevGroups) => {
          console.log("entering setGroups");
          return [{
            name: newGroup.name,
            location: newGroup.location,
            dateTime: newGroup.dateTime,
            id: grpId,
            joined: true
          },...prevGroups]
        });
      }
    } catch (error) {
      console.error("Error creating group:", error);
    }

    setShowCreateModal(false);
    setNewGroup({ name: "", dateTime: "", location: "" });
  };

  const handleDownloadInvite = async (groupId) => {
    try {
      const response = await axios.get(`${config.USER_DASH}/api/group/${groupId}/ics`, {
        responseType: 'text', 
      });
  
      const blob = new Blob([response.data], { type: 'text/calendar' });
 
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = `${groupId}-invite.ics`; 
      link.click(); 
  
      URL.revokeObjectURL(link.href);
    } catch (error) {
      console.error("Error downloading invite:", error);
      alert("Error downloading invite.");
    }
  };

  const handleJoinGroup = async (groupId) => {
    try {
      // Make an API call to join the group
      const response = await axios.post(
        `${config.USER_DASH}/group/join?groupId=${groupId}&userId=${userId}`
      );

      if (response.status === 200) {
        // On success, update the local state to mark the group as joined
        setGroups((prevGroups) =>
          prevGroups.map((group) =>
            group.id === groupId ? { ...group, joined: true } : group
          )
        );
      } else {
        console.error("Error joining group");
      }
    } catch (error) {
      console.error("Error joining group:", error);
      alert("There was an error joining the group. Please try again.");
    }
  };

  const handleSearch = (e) => {
    setSearchTerm(e.target.value.toLowerCase());
  };

  const filteredGroups = groups.filter((group) =>
    group.name.toLowerCase().includes(searchTerm)
  );

  return (
    <div className="groups-container">
      <div className="groups-header">
        <h1>Groups</h1>
        <button
          className="create-group-btn"
          onClick={() => setShowCreateModal(true)}
        >
          Create New Group
        </button>
      </div>

      <div className="groups-search">
        <Search className="search-icon" />
        <input
          type="text"
          value={searchTerm}
          onChange={handleSearch}
          placeholder="Search groups..."
          className="search-input"
        />
      </div>

      <div className="groups-list">
        {filteredGroups.length > 0 ? (
          filteredGroups.map((group) => (
            <div key={group.id} className="group-card">
              <div className="group-info">
                <div className="group-avatar">
                  <Users className="group-avatar-icon" />
                </div>
                <div className="group-details">
                  <h3>{group.name}</h3>
                  <div className="group-meta">
                    <span className="group-date">
                      <Calendar className="meta-icon" /> {group.dateTime}
                    </span>
                    <span className="group-location">
                      <MapPin className="meta-icon" /> {group.location}
                    </span>
                  </div>
                </div>
              </div>
              <div className="group-actions">
                <button
                  className={`group-action-btn ${group.joined ? "joined" : ""}`}
                  onClick={() => {
                    console.log("group joined", group)
                    if (!group.joined) {
                      handleJoinGroup(group.id);
                     
                    }
                  }}
                  disabled={group.joined}
                >
                  {group.joined ? "Joined" : "Join"}
                </button>
                <button className="group-action-btn secondary"
                 onClick={() => handleDownloadInvite(group.id)} 
                 >
                  Download Invite
                </button>
              </div>
            </div>
          ))
        ) : (
          <p className="no-results">No groups found.</p>
        )}
      </div>

      {showCreateModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Create New Group</h2>
              <button
                className="close-button"
                onClick={() => setShowCreateModal(false)}
              >
                <X className="close-icon" />
              </button>
            </div>

            <form onSubmit={handleCreateGroup} className="create-group-form">
              <div className="form-group">
                <label>Group Name</label>
                <input
                  type="text"
                  value={newGroup.name}
                  onChange={(e) =>
                    setNewGroup({ ...newGroup, name: e.target.value })
                  }
                  placeholder="Enter group name"
                  required
                />
              </div>
              <div className="form-group">
                <label>Date & Time</label>
                <input
                  type="datetime-local"
                  value={newGroup.dateTime}
                  onChange={(e) =>
                    setNewGroup({ ...newGroup, dateTime: e.target.value })
                  }
                  required
                />
              </div>
              <div className="form-group">
                <label>Location</label>
                <input
                  type="text"
                  value={newGroup.location}
                  onChange={(e) =>
                    setNewGroup({ ...newGroup, location: e.target.value })
                  }
                  placeholder="Enter location"
                  required
                />
              </div>
              <div className="modal-actions">
                <button
                  type="button"
                  className="cancel-button"
                  onClick={() => setShowCreateModal(false)}
                >
                  Cancel
                </button>
                <button type="submit" className="create-button">
                  Create Group
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Groups;
