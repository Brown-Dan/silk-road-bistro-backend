Feature: Client can create and retrieve opening hours

  Scenario: Create and retrieve opening hours
    Given a request is made to create opening hours with body
    """json
        {
      "monday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "tuesday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "wednesday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "thursday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "friday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "saturday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "sunday": {
        "openingTime": null,
        "closingTime": null,
        "closed": true
      }
    }
    """
    When a request is made to retrieve opening hours
    Then a response is returned with status 200 and opening hours
    """json
        {
      "monday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "tuesday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "wednesday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "thursday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "friday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "saturday": {
        "openingTime": "07:00:00",
        "closingTime": "19:00:00",
        "closed": false
      },
      "sunday": {
        "openingTime": null,
        "closingTime": null,
        "closed": true
      }
    }
    """