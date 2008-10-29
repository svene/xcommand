/** Shows different approaches to create a form (only the last (direct html) seems to make sense */
public class T4 extends AbstractTemplate
{
	public void execute()
	{
		new T4Header().execute();


		String action = "/thirdaction.do";
		String firstname = "sven";

/*#
	<body class="composite">
		<div id="bodycol">
			<div class="app">
				<div class="h3">
					<h3>T4</h3>
						<form action="$jv{action}">
							<input type="text" name="firstname" value="$jv{firstname}"/>
							<input type="submit" value="Submit"/>
						</form>
				</div>
			</div>
		</div>
	</body>
#*/

	}
}
