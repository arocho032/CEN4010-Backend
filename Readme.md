# CEN4010-Backend

Download Eclipse from  https://www.eclipse.org/

On the Quick Access search bar on top, look for git. If it appears (it might be installed in the 
base installation of eclipse), then skip the following step.

If it doesn't appear, install eGit:
* On Eclipse, go to Help
* Click on Eclipse Marketplace around the bottom. It should open a new window. 
* On the Find search bar, type eGit. 
* Install EGit - Git Integration for Eclipse 5.5.1

After looking for git on the Quick Access bar, it should open a new tab called Git Repository.
Click on "Clone a Git repository" to add the backend repo. If you can't find that option, 
look for the icon on the top of the tab with a green arrow. Clicking there should give you
a new window. 

On the Clone Repository Window, put the "https://github.com/arocho032/CEN4010-Backend.git"
on the URI field. It should automatically fill the other fields. Put your username and 
password on authentication section, and click Next. Continue clicking next until you 
are done with the cloning. 

Now a new git repo should appear on the Git Repositories tab called "CEN4010-Backend". 
Right click on there and go to where it says "Import Projects...". This should open
another window. Make sure only the CEN4010-Backend\SOS folder is checked. Then,
click on finish.

You should now have a project called SOS on your Package Explorer. Work on it as 
in any other IDE. 

To pull from origin, right click on the project and go all the way down to Team.
Then, click on pull. To commit your work, from Team click on commit. This opens 
a tab where you can stage (git add), commit, and push your changes. 

