# Upcoming Deadlines
{% for module in modules %}
## {{ module.module-name }}
{% for assessment in module.assessments %}
### {{ assessment.assessment-name }}
Percentage of module: {{ assessment.percentage }}%
Hand out date: **{{ assessment.hand-out-date|date:shortDate }}**
Hand in date: **{{ assessment.hand-in-date|date:shortDate }}**
{% endfor %}
{% endfor %}

