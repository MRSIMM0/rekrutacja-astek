import React from "react";
import "./style.css";
import Input from "../utils/input/input";
import InputGroup from "./inputGroup/InputGroup";
import { useState, useEffect, useRef } from "react";
import Button from "../utils/button/button";
import Receipt from "./recipt/recipt";

export default function User() {
  const [checked, setChecked] = useState(false);

  const [mileageCh, setMileageCh] = useState(false);

  const [method, setMethod] = useState("");

  const [result, setResult] = useState(0);

  const [recipes, setRecipes] = useState([
    <option default value="">
      Select up to 5 receipts
    </option>,
  ]);

  const [start, setStart] = useState(new Date(Date.now()).toUTCString());

  const [end, setEnd] = useState(new Date(Date.now()).toUTCString());

  const [choosen, setChoosen] = useState([]);

  const [receiptDTO, setReceiptDTO] = useState([]);

  const [millage, setMillage] = useState(0);

  const [nDays, setNdays] = useState(0);

  const handleChange = () => {
    setChecked(!checked);
  };

  //EKSPERYMENTALNE
  const addToChoosenEXP = (target) => {
    const temp = [...choosen];

    temp.push(<div>{target.value}</div>);
    setChoosen(temp);
    const temp2 = [...recipes];
    const filter = temp2.filter((el) => target.value != el.props.value);
    setRecipes(filter);
  };

  const deleteElement = () => {
    setChoosen([]);
    setReceiptDTO([]);
  };
  const change = (object) => {
    const temp = [...receiptDTO];
    if (temp[object.index] == undefined) {
      temp.push(object);
    } else {
      temp[object.index] = object;
    }
    setReceiptDTO(temp);
  };
  const addToChoosen = (target) => {
    const temp = [...choosen];
    if (temp.length < 5) {
      temp.push(
        <Receipt
          index={temp.length}
          deleteEl={(object) => deleteElement(object)}
          name={target.value}
          handleInput={(obj) => change(obj)}
        />
      );
      setChoosen(temp);
    }
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/getAllData")
      .then((res) => res.json())
      .then((results) => {
        const temp = [...recipes];

        results.availableReceipts.forEach((el) => {
          temp.push(<option value={el.receiptName}>{el.receiptName}</option>);
        });
        setRecipes(temp);
      });
  }, []);

  const calculate = () => {
    const body = {
      receipts: receiptDTO,

      period: nDays,

      personalCarMillage: millage,
    };

    fetch("http://localhost:8080/api/calculate", {
      method: "POST",
      body: JSON.stringify(body),
    })
      .then((response) => response.json())
      .then((results) => {
        setResult(results.result);
      });
  };

  const date = (
    <div className="date">
      <input
        type="date"
        id="start"
        value={start}
        onChange={(event) => {
          setStart(event.target.value);

          const date1 = new Date(event.target.value);
          const date2 = new Date(end);

          const Difference_In_Time = date2.getTime() - date1.getTime();
          const Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);

          setNdays(Difference_In_Days);
        }}
      />
      <input
        type="date"
        id="end"
        onChange={(event) => {
          setEnd(event.target.value);
          const date1 = new Date(start);
          const date2 = new Date(event.target.value);

          const Difference_In_Time = date2.getTime() - date1.getTime();
          const Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);

          setNdays(Difference_In_Days);
        }}
        value={end}
        min={start}
      ></input>
    </div>
  );

  const dayss = (
    <div className="input-small">
      <Input onChange={(e) => setNdays(e)} max="999"></Input>
    </div>
  );

  const mileage = (
    <div className="center">
      <div className="input-small">
        <Input onChange={(e) => setMillage(e)} max="999"></Input>
      </div>
    </div>
  );

  let content = "";

  if (method == "period") {
    content = date;
  }

  if (method == "days") {
    content = dayss;
  }

  const days = checked ? (
    <div class="center">
      <div
        onChange={(event) => {
          setMethod(event.target.value);
        }}
        class="choose"
      >
        <InputGroup
          name="Period"
          value="period"
          type="radio"
          radioName="period"
        ></InputGroup>
        <InputGroup
          name="Number of days"
          value="days"
          type="radio"
          radioName="period"
        ></InputGroup>
      </div>
      <div>{content}</div>
    </div>
  ) : (
    ""
  );

  return (
    <div class="user">
      <div class="title-user">Reimbursement Calculation</div>
      <div class="input-group">
        <div class="wrapper">
          <select
            value=""
            onChange={(event) => {
              addToChoosen(event.target);
            }}
          >
            {recipes}
          </select>
        </div>
      </div>
      {choosen.length > 0 ? (
        <div class="list-wrap flex-column">
          <div class="receipts-list">{choosen}</div>
          <div class="clear">
            <Button action={() => deleteElement()}>Clear</Button>
          </div>
        </div>
      ) : (
        ""
      )}

      <div class="input-wrapper">
        <div class="input-group-chek">
          <div class="input-name">Daily reimbursement</div>
          <input
            onChange={() => {
              handleChange();
            }}
            value={checked}
            type="checkbox"
          ></input>
        </div>
      </div>
      {days}
      <div className="pair">
        <div class="input-wrapper">
          <div class="input-group-chek">
            <div class="input-name">Personal car mileage</div>
            <input
              onChange={() => {
                setMileageCh(!mileageCh);
              }}
              value={mileageCh}
              type="checkbox"
            ></input>
          </div>
        </div>
        {mileageCh ? mileage : ""}
      </div>

      <div className="calculate">
        <Button
          action={() => {
            calculate();
          }}
        >
          Calculate
        </Button>
      </div>
      {result != 0 ? (
        <div className="result">Reimbursement: {result} USD</div>
      ) : (
        ""
      )}
    </div>
  );
}

{
  /* <div class="input-wrapper">
   <div class="input-group-chek">
     <div class="input-name">Choose period</div>
     <input type="checkbox"></input>
   </div>
   <div class="input-group">
     <input type="date" id="start" name="trip-start" />
     <input type="date" id="end" name="trip-start" />
   </div>
 </div>
 <div class="input-wrapper">
   <div class="input-group-chek">
     <div class="input-name">Enter number of days</div>
     <input type="checkbox" />
   </div>
   <div class="input-group">
     <div class="input-small">
       <Input min="0" type="number" />
     </div>
   </div>
 </div>
 <div class="input-wrapper">
   <div class="input-group-chek">
     <div class="input-name">Personal car mileage</div>
     <input type="checkbox" />
   </div>
   <div class="input-group">
     <div class="input-small">
       <Input min="0" type="number" />
     </div>
   </div>
 </div>
 <div class="input-group">
   <div class="input-group-chek">
     <div class="input-name">Calculate total amount </div>
     <input type="checkbox" />
   </div>
</div>
 <button>Submit</button>  */
}
