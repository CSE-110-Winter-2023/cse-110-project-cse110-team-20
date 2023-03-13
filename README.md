# CSE 110 team 20


## [Next Due: **MS 2 Delivery**  Mar 16th Thursday by 11:55pm](https://canvas.ucsd.edu/courses/42716/assignments/567150)
### [Software design – 21 points](https://github.com/CSE-110-Winter-2023/cse-110-project-cse110-team-20/milestone/2)


**To-do list:**
- [ ] 1’ – Tidy code (indented, naming conventions, file/method-level comments as needed)
- [ ] 10’ – Extensive application of SRP and DRY
- [ ] 4’ – Evidence of OOD (objects and messages sounds like requirements)
- [ ] 4’ – Appropriate use of design patterns and/or dependency inversion to achieve OCP and SRP
- [ ] 2' – Adapter/Mock of Nearby APIs to support both the real Nearby functionality and testing/demoing


### [Demo satisfaction of all milestone requirements – 35 points (order is priority)](https://github.com/CSE-110-Winter-2023/cse-110-project-cse110-team-20/milestone/2)
> **_NOTE:_** : Regression (0 pts) - we will only use MS 1 features that are required to exercise MS 2's features


**To-do list:**
- [ ] Story 12a: Display direction of one friend (rather than home) [H] - 4 pts

  As a user I want when the app is launched I want to see in which direction my best friend (or parent) is are so that I can think about what they are doing


- [ ] Story 12b: Continuously display direction of one friend  [H] - 6 pts

  As a user I want my friend's changes in location to be reflected on the display so that I can continue to think about what they are doing


- [ ] Story 12c: Continuously directions of multiple friends [H] - 5 pts

  As a user I want to see in which directions my friends are so that I can think about what they are all doing



- [ ] Story 13: Convey distance of friends [H] - 4 pts

  As a user I want a sense of how far away friends are (close versus nearby) so that I can better judge whether I should try to track them down (see Figure (a))
  Note that circle's perimeter is now a boundary between relative distances, see @391, #4.



- [ ] Story 14: Handle loss of GPS signal [L] - 2 pts

  As a user I want it that when I don't have a GPS signal that the app keeps using my last known GPS location and keeps me aware that I don't have GPS so that I can be wary of the directions indicated, so maybe I won't try to track my friend down


- [ ] Story 14b: Net time of GPS signal loss [L] - 1 pt

  As a user I want to be aware of how long I haven't had GPS signal so that I can know how much trust I can put in the directions indicated to perhaps allow me to track them down



- [ ] Story 15: Zoom out [M] - 4 pts

  As a user I want to be able to zoom the view out to display far-away and very far-away friends so that I can get see which far-away friends are in which direction to reflect on my relationship with them (Figure (c) and see @391, #4.)



- [ ] Story 15b: Zoom in [M] - 1 pts

  As a user I want to be able to zoom the view in so that I can get more detail on where my close-by friends are, perhaps so that I can meet up with them


- [ ] Story 16: Indicator of far-away friends [L] - 2 pt

  As a user I want to see a small directional indicator that far-away friends aren't displayed so that I can know that there are more friends to see without cluttering the view (Figure (b))




- [ ] Story 17a: Truncate labels that will overlap [L] - 2 pts

  As a user I want the label of a friend truncated if it will run into another friend's label so that I can tell who the other friend is (Figure (a))



- [ ] Story 17b: "Stack" labels that are in same orientation and circle [L] - 2 pts

  As a user I want it that so if two friends are in the same circle and roughly the same orientation that their labels are nudged away from each other (one closer to the center, the other farther) so that I can tell who both friends are (See Figure below)


- [ ] Story 18: Default Zoom is inner two levels [L] - 1 pt

  As a user I want the zoom level at app launch to be the inner two levels so that I can initially be focused on the friends who are closest to me (Figures (a) and (b))


- [ ] Story 19: Mocking for Demo [H] - 1 pt

  As an Instructor I want to be able to change the SC2 web server endpoint (URL) so that I can test all the new app features quickly


- [ ] Story 20: No Mocking of Orientation [H] - 0 pts

  As an Instructor I want to use the Android emulator to control orientation rather than a custom Mock so that the app's implementation and design are compromised as little as possible







### [Testing – 24 points](https://github.com/CSE-110-Winter-2023/cse-110-project-cse110-team-20)


**To-do list:**
- [ ] 8’ – Automated Story Testing - at least one BDD scenario per story (with JUnit, Espresso, Robolectric, etc., doesn't matter)
- [ ] 8' – App/classes designed for testability/demo (Mocking for Nearby Messages - see instructor Stories below))
- [ ] 4' – Local testing: All tests automated, tied into JUnit, Espresso, or Robolectric (run and show pass/fail)
- [ ] 4' – Continuous Integration: All non-instrumented tests run on GitHub Actions CI


### [Github Project – 4 points](https://github.com/CSE-110-Winter-2023/cse-110-project-cse110-team-20)


**To-do list:**
- [ ] 1’ – Github Project tidy (all items are where they belong, in right order)
- [ ] 1’ – Tasks assigned to developers
- [ ] 2’ – Burn down chart looks good (steady progress, not all work at end)



### [GitHub – 5 points](https://github.com/CSE-110-Winter-2023/cse-110-project-cse110-team-20)


**To-do list:**
- [ ] 1' - Protected master branch (pull requests passed tests on GitHub Actions CI and passed code review)
- [ ] 2' – Written code reviews for all merged Story branches
- [ ] 1' - Merged protected branch story-by-story
- [ ] 1' - Each push labelled with its Github issue number


**To be continued →**


