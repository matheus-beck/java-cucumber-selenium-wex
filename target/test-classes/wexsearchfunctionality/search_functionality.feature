Feature: Search Functionality
  It tests the search functionality of the WEX website

  @ID-0001
  Scenario: The searched word should appear in the title and in the navigation section after ‘Search results for’.
    Given I am on the WEX website
    When I search on the page for "<search_word>"
    Then the page title should be "You searched for <search_word> | WEX Inc."
    Then the navigation section element should contain "Search results for '<search_word>'"
    Examples:
      | search_word |
      | card        |

  @ID-0002
  Scenario: A 'no results message' should be raised in cases of invalid inputs.
    Given I am on the WEX website
    When I search on the page for "<search_word>"
    Then the search should not return results
    Examples:
      | search_word     |
      | ThisIsAFakeWord |
      | 12345678910     |

  @ID-0003
  Scenario: The search must return a maximum of 10 posts per page and a navigation bar if needed.
    Given I am on the WEX website
    When I search on the page for "<search_word>"
    Then the search must return a maximum of 10 posts per page
    Then the search must return a navigation bar
    Examples:
      | search_word |
      | card        |
      |             |

  @ID-0004
  Scenario: The searched word should appear in the title or in the body text of a post returned.
    Given I am on the WEX website
    When I search on the page for "<search_word>"
    Then the "<search_word>" word should appear in the title or in the body text of the "<post_to_verify>" post returned
    Examples:
      | search_word | post_to_verify |
      | card        | 1 st           |
      | fleet       | 2 nd           |
      | 10          | 3 rd           |
      | service     | 4 th           |
      | company     | 5 th           |
      | car         | 6 th           |
      | man         | 7 th           |
      | woman       | 8 th           |
      | mobile      | 9 th           |
      | try         | 10 th          |
