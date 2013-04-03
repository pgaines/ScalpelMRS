ScalpelMRS
============

### The goal of ScalpelMRS is to implement a Medical Record System that can be deployed on a cloud application server such as Amazon Web Services.

The ScalpelMRS Project draws upon the following references and examples:<br>
<ul><li>The <a href="https://www.niem.gov" target="_blank">National Information Exchange Model</a></li>
<li><a href="http://openmrs.org/" target="_blank">OpenMRS</a></li>
<li><a href="http://open-emr.org/" target="_blank">OpenEMR</a></li>
<li><a href="http://www.openehr.org" target="_blank">openEHR</a></li>
</ul>

##Features

Planned for v 0.1.0:<br>
<ul><li>Stores blood pressure, sugar level, and weight</li>
<li>Stores prescriptions</li>
<li>Doctors can enter any comments and observations</li>
<li>Store patient information such as address, phone number, and insurance info in addition to health records</li>
<li>Doctors can pull information of the patient using the software and check the progress or trends in health indicators of the patient.</li>
<li>Patient can enter daily blood pressure and sugar levels if a doctor directs the patient to do so</li>
<li>Patient can check the fluctuation of his/her own health indicators.<br>
</li></ul>

##Setup workflow

An explanation of the steps I followed to create the ScalpelMRS project. You don’t have to follow these instructions, but it’ll help everyone to understand how the project was set up. This guide assumes you've read and followed all the setup steps in 1) START HERE - Setup

* Created a new Java/Play2.1 project by following Typesafe instructions: http://typesafe.com/resources/tutorials/getting-started-with-play-java.html
	* I opened the command prompt
	* I navigated to C:\Users\KGB\workspace
	* I typed ‘g8 typesafehub/play-java’ as per the instructions
	* I named the project ScalpelMRS and placed it at C:\Users\KGB\workspace\ScalpelMRS
	* At this point a workaround is required, see: http://stackoverflow.com/questions/11969375/play-idea-failing-no-sbt-idea-plugin-for-this-version-of-sbt-0-11-3
	* I opened C:\Users\KGB\workspace\ScalpelMRS\project\plugins.sbt in a text editor.
	* I changed line 8 to 'addSbtPlugin("play" % "sbt-plugin" % "2.0.1")' without the single quotes, and saved.
	* I navigated to 'C:\Users\KGB\workspace\ScalpelMRS' and typed ‘sbt’ to open the sbt console. At this point it'll take some time resolving all the dependencies and do some further project setup.
	* Once it was done, I have the command prompt: [ScalpelMRS] $
	* I then start ScalpelMRS in development mode (which means the application is recompiled every time you refresh the browser window) by typing 'run'.
	* To verify that I can connect to the web application, I open http://localhost:9000 in a browser.
	* It loaded, and told me about a compilation error; line 5 of index.scala.html.
	* I changed line 5 of index.scala.html to '    <h1>@message</h1>', and the compilation error was resolved upon refreshing the browser window (I see 'Your new application is ready.')
	* I hit Ctrl+D to terminate the ScalpelMRS web application.
* Set up the project to work with Intellij Idea 12:
	* I then type 'idea' to generate the ScalpelMRS.iml project file, so the project can be imported to Intellij Idea.
	* I open Intellij Idea 12.
	* I go to File > Import Project, navigate to C:\Users\KGB\workspace, click ScalpelMRS, and click Ok.
	* I click Next > Next > Next > Next > Overwrite > Next > Finish > This Window.
* Set up the project to work with GitHub for Windows:
	* I open Github for Windows.
	* I click '+add' to create a new local repository
	* I type 'ScalpelMRS' as the name, and make sure that the place where the repository will be stored is 'C:\Users\KGB\workspace\ScalpelMRS'
	* I saved LICENSE.md to the ScalpelMRS project directory.
	* I go back to Github for Windows, click 'pgaines' under 'github', and click the blue arrow to 'open this repo'.
	* I make the initial ScalpelMRS commit to the public github at http://github.com/pgaines/ScalpelMRS .
	* I click "publish".
* Then continue by following the rest of the PlayFramework.com Java Guide.
