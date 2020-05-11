# Build a docker image based on the latest RabbitMQ Managemnet and the 
# necessary plugins

# Build: docker build -t "rabbitmq:test" .
# Run: docker run -d -p 15672:15672 -p 5672:5672 -t "rabbitmq:test" 
# two ports are exposed 15672 (web console) and 5672 (amqp)

FROM rabbitmq:management
RUN rabbitmq-plugins enable --offline rabbitmq_jms_topic_exchange rabbitmq_amqp1_0
