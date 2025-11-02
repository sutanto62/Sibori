### **Product Requirements Document: Sibori - The Priest's Assistant App**

---

### **1. Introduction**

Sibori is a mobile application designed to support "Special Ordinary Ministers of Holy Communion" (or other lay assistants) in their service to the parish. The app aims to simplify the management of ministry schedules, facilitate communication for schedule changes, and ensure assistants are well-prepared for their duties through timely reminders. This will reduce the administrative burden on both the assistants and the parish staff.

### **2. Goals and Objectives**

* **Primary Goal:** To provide a centralized and easy-to-use platform for priest assistants to manage their ministry schedules.
* **Objectives:**
    *   To streamline the viewing and management of ministry schedules.
    *   To create an efficient process for requesting and fulfilling schedule replacements.
    *   To reduce missed assignments by sending timely notifications and reminders.
    *   To improve communication between assistants and the parish administration.

### **3. Target Audience**

* **Primary Users:** Lay assistants, such as Special Ordinary Ministers of Holy Communion, who are scheduled for various ministries.
* **Secondary Users:** Parish
 administrators or schedulers who are responsible for creating and managing the ministry schedules.

### **4. Features**

#### **MVP (Minimum Viable Product) Features**

*   **User Authentication:**
    *   Secure sign-in for registered assistants.
    *   Password reset functionality.

* **Ministry Schedule:**
    *   A clear and intuitive view (list or calendar) of the user's personal ministry schedule (date, time, and type of service).
    *   A view of the full parish ministry schedule.

*   **Schedule Replacement Request:**
    *   A feature for
 an assistant to request a substitute for a specific assignment.
    *   The request should notify other qualified assistants.
    *   A mechanism for another assistant to accept the open assignment.
    *   The system should update the schedule automatically and notify all relevant parties (original assistant, substitute, and administrator) upon confirmation.


*   **Notifications:**
    *   Automated push notifications to remind the user of their scheduled ministry (e.g., one week before and 24 hours before).
    *   Notifications for new replacement requests and confirmations.

*   **User Profile:**
    *   A simple profile page where users can view
 their contact information.

#### **Post-MVP Features (Future Enhancements)**

*   **Direct Messaging:** In-app chat for quick communication between assistants.
*   **Availability Preferences:** A feature for assistants to set their general availability or block out dates, which schedulers can see.
*   **
Document Hub:** A section for important documents, guidelines, or prayers.
*   **Admin Dashboard:** A web-based interface for parish administrators to manage schedules, users, and send announcements.

### **5. User Flow**

1.  **Onboarding:** The user receives an invitation, registers, and logs into
 the app.
2.  **Viewing Schedule:** Upon logging in, the user lands on a dashboard showing their next upcoming assignment and a link to view their full schedule.
    * Confirm the assignment and update secondary user with notification.
    * The assignment mark as confirmed
3.  **Requesting a Replacement:**
    *   The user navigates to a scheduled assignment they cannot make.
    * They tap a "Request Replacement" button.
    * The assignment becomes available to other assistants.
4.  **Accepting a Replacement:**
    *   Another assistant sees the open assignment in a dedicated "Open Needs" section.
    *   They accept the assignment. The schedule is updated for
 both users, and a confirmation is sent.

### **6. Non-Functional Requirements**

*   **Platform:** Android.
*   **Security:** All user data and communication should be encrypted.
*   **Usability:** The interface should be clean, simple, and accessible to users who may not be tech
-savvy.
*   **Reliability:** The app must be stable and available, especially during peak times (e.g., weekends).
* **Supabase** for database
* **Newrelic** for APM
* **Statsig/Posthog** for product metrics

### **7. Success Metrics**

*   **Adoption Rate:** Percentage of assistants actively using the app.
*   **User Engagement:** Daily and Monthly Active Users (
DAU/MAU).
*   **Feature Success:**
    *   Number of successful replacement requests handled via the app per month.
    *   A decrease in the number of missed or late assignments (can be tracked via parish feedback).
*   **User Satisfaction:** Positive app store ratings and feedback
 from users.