#### {{ assessment.assessment-name }}
Percentage of module: {{ assessment.percentage }}%
{% if assessment|is-exam? %}
*This assessment is an exam, and as such it will take place in the exam period. The timetable for this is released separately.*
{% else %}
Hand out date: **{{ assessment.hand-out-date|date:shortDate }}**
Hand in date: **{{ assessment.hand-in-date|date:shortDate }}**
{% endif %}
