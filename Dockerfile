FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy your project files to the container
COPY . /app

# Build or compile your project (if required)
# For example, if you have a Java project, you can use:
# RUN javac Main.java

# Specify the command to run when the container starts
CMD ["java", "Main"]