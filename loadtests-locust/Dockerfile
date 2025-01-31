FROM python:3.10.0

# Set working directory
WORKDIR /mnt/locust

# Copy necessary files
COPY requirements.txt /mnt/locust/

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the Locust code
COPY . /mnt/locust/

# Install locust
RUN pip install locust

# # Default command
# CMD ["locust", "-f", "locust_petclinic_test.py", "--host=http://apprest:9966", "--headless", "-u", "10", "-r", "2", "--run-time", "5m"]
