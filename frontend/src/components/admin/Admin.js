import React from "react";
import "./style.css";
import { useState, useEffect } from "react";
import Button from "../utils/button/button";
import Receipt from "./recipt/Receipt";
import Input from "../utils/input/input";
import Limit from "./recipt/Limit";
export default function Admin() {
  const [daily, setDaily] = useState(0);
  const [millage, setMillage] = useState(0);
  const [receipts, setReceipts] = useState([]);
  const [recipt, setReceipt] = useState("");
  const [limitTypes, setLimitTypes] = useState([
    <option default value="">
      -- Please choose limit Type --
    </option>,
  ]);
  const [limits, setLimits] = useState([]);
  const [limitType, setLimitType] = useState("");
  const [limit, setLimit] = useState(0);
  const handleChange = (e) => {
    setLimitType(e);
  };

  const handleLimit = (val) => {
    setLimit(val);
  };

  const addLimit = (limit, type) => {
    var bod = {
      limitType: type,
      limit: limit,
    };

    fetch("http://localhost:8080/api/addLimit", {
      method: "POST",
      headers: {
        User: "Admin",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(bod),
    })
      .then((response) => response.json())
      .then((results) => {
        const lim = [];
        results.forEach((element) => {
          lim.push(
            <Limit
              getLim={(e) => {
                deleteLimit(e);
              }}
              name={element.limitType}
              limit={element.limit}
            ></Limit>
          );
        });

        setLimits(lim);
      });
  };

  const uploadAllowance = (allowance) => {
    var bod = {
      allowenceRade: allowance,
    };
    fetch("http://localhost:8080/api/dailyAllowance", {
      method: "POST",
      headers: {
        User: "Admin",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(bod),
    })
      .then((response) => response.json())
      .then((res) => {
        setDaily(res.allowenceRade);
        setMillage(res.dailyMillage);
      });
  };

  const handleRec = (res) => {
    const pl = [...receipts];
    res.forEach((element) => {
      pl.push(
        <Receipt
          getRec={(res) => handleRec(res)}
          name={element.receiptName}
          limit={element.limit}
        ></Receipt>
      );
    });
    setReceipts(pl);
  };

  const uploadMillage = (millage) => {
    var bod = {
      dailyMillage: millage,
    };

    fetch("http://localhost:8080/api/dailyMillage", {
      method: "POST",
      headers: {
        User: "Admin",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(bod),
    })
      .then((response) => response.json())
      .then((res) => {
        setDaily(res.allowenceRade);
        setMillage(res.dailyMillage);
      });
  };

  const addRecipt = (name) => {
    var bod = {
      receiptName: name,
    };

    fetch("http://localhost:8080/api/addRecipe", {
      method: "POST",
      headers: {
        User: "Admin",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(bod),
    })
      .then((response) => response.json())
      .then((res) => {
        handleRec(res);
        setReceipt("");
      });
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/getAllData")
      .then((res) => res.json())
      .then((results) => {
        setDaily(results.allowenceRade);
        setMillage(results.dailyMillage);

        const pl = [...receipts];
        results.availableReceipts.forEach((element) => {
          pl.push(
            <Receipt
              getRec={(res) => handleRec(res)}
              name={element.receiptName}
              limit={element.limit}
            ></Receipt>
          );
        });
        setReceipts(pl);

        const l = [...limitTypes];

        results.limitTypes.forEach((element) => {
          l.push(<option value={element}>{element}</option>);
        });
        setLimitTypes(l);

        const lim = [...limits];
        results.limits.forEach((element) => {
          lim.push(
            <Limit
              getLim={(e) => {
                deleteLimit(e);
              }}
              name={element.limitType}
              limit={element.limit}
            ></Limit>
          );
        });

        setLimits(lim);
      });
  }, []);

  const deleteLimit = (limit) => {
    const lim = [...limits];
    limit.forEach((element) => {
      lim.push(
        <Limit
          getLim={(e) => {
            deleteLimit(e);
          }}
          name={element.limitType}
          limit={element.limit}
        ></Limit>
      );
    });

    setLimits(lim);
  };

  return (
    <div className="admin">
      <div className="input-group">
        <div className="input-name">Daily allowance</div>
        <div className="input-field">
          <Input
            placeholder={daily}
            onChange={(value) => {
              setDaily(value);
            }}
            min="0"
            type="number"
          />
        </div>
        <div>USD</div>
        <div className="add">
          <Button
            action={() => {
              uploadAllowance(daily);
            }}
          >
            SAVE
          </Button>
        </div>
      </div>
      <div className="input-group">
        <div className="input-name">Daily millage</div>

        <div className="input-field">
          <Input
            placeholder={millage}
            onChange={(value) => {
              setMillage(value);
            }}
            min="0"
            type="number"
          />
        </div>
        <div>USD</div>
        <div className="add">
          <Button
            action={() => {
              uploadMillage(millage);
            }}
          >
            SAVE
          </Button>
        </div>
      </div>
      <div className="receipts-type-group">
        <div className="input-group">
          <div className="input-name">Add receipt</div>
          <div className="input-reciept">
            <Input
              onChange={(value) => {
                setReceipt(value);
              }}
              placeholder="Reciept name"
              type="text"
            />
          </div>
          <div className="add">
            <Button action={() => addRecipt(recipt)}> ADD</Button>
          </div>
        </div>
        <div className="list-wrapper">
          <div className="receipts-list">{receipts}</div>
        </div>
        <div className="receipts-type-group">
          <div className="input-name">Add limit</div>

          <div className="input-group">
            <select
              onChange={(event) => handleChange(event.target.value)}
              className="select"
            >
              {limitTypes}
            </select>
            <div className="input-reciept">
              <Input
                onChange={(val) => handleLimit(val)}
                placeholder="Limit [USD]"
                type="number"
                min="0"
              />
            </div>
            <div className="add">
              <Button action={() => addLimit(limit, limitType)}>ADD</Button>
            </div>
          </div>
          <div className="list-wrapper">
            <div className="receipts-list">{limits}</div>
          </div>
        </div>
      </div>
    </div>
  );
}
