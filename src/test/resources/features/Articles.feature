Feature: Client can create and retrieve an article

  Scenario: Create an article
    Given a request is made to create an article with body
    """json
        {
          "title" : "Breaking news!",
          "content" : "This is content"
        }
    """
    When a request is made to retrieve recent articles with limit 1
    Then a response is returned with status 200 and body
        """json
          {
              "articles": [
                  {
                      "title": "Breaking news!",
                      "content": "This is content"
                  }
              ]
          }
    """