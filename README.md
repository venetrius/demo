# ArgueWise

## Introduction
ArgueWise is a platform designed to foster structured, respectful, and productive discussions among individuals or groups with differing opinions. It's built to encourage understanding, critical thinking, and help participants find common ground or develop new ideas.

## Key Features
- **Structured Discussions**: Spaces for focused topics with specific rules.
- **Argument Management**: Present, list, and rank arguments based on relevance.
- **Time-Limited Discussions**: Specify durations and statuses for discussions.
- **API-Driven Architecture**: Manage users, spaces, discussions, and more.
- **OpenAI Integration**: Generate content via the OpenAI API.

## Technologies
- **Back-End**: Java Spring Boot
- **Front-End**: React
- **Database**: PostgreSQL
- **Email Functionality**: Kafka and MailGun

## Getting Started

### Prerequisites
- Java JDK
- PostgreSQL
- Node.js and npm
- Kafka (for email functionality)
- MailGun account (for email functionality)

### Setup

#### Back-End
1. Clone the repository:
   ```bash
   git clone https://github.com/venetrius/demo.git
   ```
2. Navigate to the project directory:
   ```bash
   cd demo
   ```
3. Copy the template configuration file:
   ```bash
   cp src/main/resources/application-template.yml src/main/resources/application.yml
   ```
4. Edit `src/main/resources/application.yml` with the necessary environment variables and dependencies:

5. Build the project (excluding tests):
   ```bash
   ./gradlew build -x test
   ```
6. Run the application:
   ```bash
   java -jar ./build/libs/ArgueWise-0.0.1-SNAPSHOT.jar
   ```

#### Front-End
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the React app:
   ```bash
   npm start
   ```

### Usage
1. Open http://localhost:3000/ in a web browser after starting the back-end and front-end.
2. Click 'login/register' to create a new account.
3. Log in to the platform.
4. Create a new space, start a discussion, and add arguments.

## License
The licensing for ArgueWise is currently under consideration and will be updated accordingly.
