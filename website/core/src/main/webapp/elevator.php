<?php $title = 'Elevator Pitch Cartoon'; require_once 'include/page-start.php'; ?>

	<?php $useQuickStart = true; require_once 'include/body-start.php'; ?>
	
		<h2>Elevator Pitch Cartoon</h2>

		<p>
			The Metawidget 'elevator pitch' gives a quick overview of Metawidget in the time it takes to ride an elevator:
		</p>

		<div id="elevator">

			<div id="frame1" class="frame">
				<div class="bob-bubble">
					<div class="bubble">
						Morning John
					</div>
					<div class="arrow-down"></div>
				</div>
				<div class="john-bubble">
					<div class="bubble">
						Morning Bob
					</div>
					<div class="arrow-down"></div>
				</div>
				<img src="media/elevator/frame1.png" alt=""/>
			</div>

			<div id="frame2" class="frame">
				<div class="john-bubble">
					<div class="bubble">
						Hey, so what's that Metawidget stuff you've been
						using, Bob?
					</div>
					<div class="arrow-down"></div>
				</div>
				<img src="media/elevator/frame2.png" alt=""/>
			</div>

			<div id="frame3" class="frame">
				<div class="bob-bubble">
					<div class="bubble">
						Metawidget is UI generation done right! It treats UI
						generation as an everyday part of the software stack,
						so it has a different philosophy to other products
					</div>
					<div class="arrow-down"></div>
				</div>
				<div class="john-bubble">
					<div class="bubble">
						In what way?
					</div>
					<div class="arrow-down"></div>
				</div>
				<img src="media/elevator/frame3.png" alt=""/>
			</div>

			<div id="frame4" class="frame">
				<div class="bob-bubble">
					<div class="bubble">
						3 ways: first, UI generation shouldn't use
						static code generation!
						That may give you a headstart, but
						then you need to tweak the generated
						code, and regeneration gets cumbersome
					</div>
					<div class="arrow-left"></div>
				</div>
				<img src="media/elevator/frame4.png" alt=""/>
			</div>

			<div id="frame5" class="frame">
				<div class="john-bubble">
					<div class="bubble">
						Static generation is like a friend who can start you on the road,
						but can't stay for the journey
					</div>
					<div class="arrow-down"></div>
				</div>
				<div class="bob-bubble">
					<div class="bubble">
						Exactly. UI generation should be done at <em>runtime</em>
					</div>
					<div class="arrow-down"></div>
				</div>
				<img src="media/elevator/frame5.png" alt=""/>
			</div>

			<div id="frame6" class="frame">
				<div class="bob-bubble">
					<div class="bubble">
						Second, UI generation shouldn't 'own' the UI! You can't expect to generate everything - UIs are
						full of asthetics and psychology. The pieces you <em>can</em> automate have to
						fit nicely alongside other screen elements
					</div>
					<div class="arrow-down"></div>
				</div>
				<img src="media/elevator/frame6.png" alt=""/>
			</div>

			<div id="frame7" class="frame">
				<div class="bob-bubble">
					<div class="bubble">
						Finally, UI generation should be a regular part of the software stack. It
						should work with <em>your</em> choice of persistence layer,
						business layer, UI framework...
					</div>
					<div class="arrow-down"></div>
				</div>
				<div class="john-bubble">
					<div class="bubble">
						...not dictate them
					</div>
					<div class="arrow-right"></div>
				</div>
				<div class="bob-bubble bob-bubble2">
					<div class="bubble">
						Exactly!
					</div>
					<div class="arrow-left"></div>
				</div>
				<img src="media/elevator/frame7.png" alt=""/>
			</div>

			<div id="frame8" class="frame">
				<div class="john-bubble">
					<div class="bubble">
						Well, that sounds cool, maybe I should give it a try
					</div>
					<div class="arrow-down"></div>
				</div>
				<div class="bob-bubble">
					<div class="bubble">
						It's been working great for us - let me know how it goes for you! See you later John
					</div>
					<div class="arrow-down"></div>
				</div>
				<img src="media/elevator/frame8.png" alt=""/>
			</div>

		</div>

		<p style="margin: auto">
			Want to be like John and give Metawidget a try? Download the <a href="/download.php">distribution</a>!
		</p>		

	<?php require_once 'include/body-end.php'; ?>		

<?php require_once 'include/page-end.php'; ?>
