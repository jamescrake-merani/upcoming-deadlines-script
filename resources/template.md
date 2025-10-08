# Upcoming Deadlines
{% for module in modules %}
## {{ module.module-name }}
### Handed out
{% for assessment in module.assessments.handed-out %}
{{ include "assessment_details.md" with assessment=assessment }}
{% endfor %}
### Not handed out yet
{% for assessment in module.assessments.not-handed-out %}
{{ include "assessment_details.md" with assessment=assessment }}
{% endfor %}
### Already due in
{% for assessment in module.assessments.not-handed-out %}
{{ include "assessment_details.md" with assessment=assessment }}
{% endfor %}
{% endfor %}

